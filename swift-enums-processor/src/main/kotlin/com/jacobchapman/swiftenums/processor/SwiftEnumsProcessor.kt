package com.jacobchapman.swiftenums.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jacobchapman.swiftenums.SwiftEnum

private const val TAB = "    "

class SwiftEnumsProcessor(private val codeGenerator: CodeGenerator, private val options: Map<String, String>) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(SwiftEnum::class.qualifiedName.toString())
            .filterIsInstance<KSClassDeclaration>()
        if (!symbols.iterator().hasNext()) return emptyList()
        val moduleName = options[OPTION_MODULE_NAME]
        // get all the subclasses
        codeGenerator.createNewFile(Dependencies(false), "", "${moduleName}EnumMappers", "swift").writer().use { writer ->
            // build the error to throw
//            enum ToEnumError : Error {
//                case UnknownType
//            }
            writer.appendLine("enum ToEnumError : Error {")
            writer.appendLine("${TAB}case UnknownType")
            writer.appendLine("}")
            symbols.forEach { ksClassDeclaration ->
                val enumName = "${ksClassDeclaration.simpleName.asString()}Enum"
                writer.appendLine("enum ${enumName} {")
                val classesToMap = mutableListOf<KSClassDeclaration>()
                ksClassDeclaration.getSealedSubclasses().forEach { sealedClass ->
                    // add a new case for sealed class
                    val caseName = sealedClass.simpleName.asString()
                    val properties = sealedClass.getAllProperties().map { ksPropertyDeclaration ->
                        "${ksPropertyDeclaration.simpleName.asString()}: ${ksPropertyDeclaration.type.toString()}"
                    }.toList()
                    if (properties.isNotEmpty()) {
                        writer.appendLine("${TAB}case ${caseName.lowercase()}(${properties.joinToString()})")
                    } else {
                        writer.appendLine("${TAB}case ${caseName.lowercase()}")
                    }
                    classesToMap.add(sealedClass)
                }
                // close the enum class
                writer.appendLine("}")
                // start the mappers
                writer.appendLine("extension ${ksClassDeclaration.simpleName.asString()} {")
                writer.appendLine("${TAB}func toEnum() throws -> $enumName {")
                writer.appendLine("${TAB}${TAB}switch self {")
                classesToMap.forEach { sealedClass ->
                    val caseName = sealedClass.simpleName.asString()
                    val properties = sealedClass.getAllProperties().map { ksPropertyDeclaration ->
                        "${ksPropertyDeclaration.simpleName.asString()}: ${caseName.lowercase()}.${ksPropertyDeclaration.simpleName.asString()}"
                    }.toList()
                    if (properties.isNotEmpty()) {
                        writer.appendLine("${TAB}${TAB}${TAB}case let ${caseName.lowercase()} as ${ksClassDeclaration.simpleName.asString()}.${caseName}:")
                        writer.appendLine("${TAB}${TAB}${TAB}${TAB}return $enumName.${caseName.lowercase()}(${properties.joinToString()})")
                    } else {
                        writer.appendLine("${TAB}${TAB}${TAB}case _ as ${ksClassDeclaration.simpleName.asString()}.${caseName}:")
                        writer.appendLine("${TAB}${TAB}${TAB}${TAB}return $enumName.${caseName.lowercase()}")
                    }
                }
                // add default case to throw
                /*
                        default:
                            throw ToEnumError.UnknownType
                 */
                writer.appendLine("${TAB}${TAB}${TAB}default:")
                writer.appendLine("${TAB}${TAB}${TAB}${TAB}throw ToEnumError.UnknownType")
                writer.appendLine("${TAB}${TAB}}")
                writer.appendLine("${TAB}}")
                writer.appendLine("}")
            }
        }
        return emptyList()
    }

    companion object {
        const val OPTION_MODULE_NAME = "option_module_name"
    }
}

