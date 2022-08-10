import {evaluation} from "../processors/js-compiler-processor.js";
import {customPlugins} from "../processors/html-compiler-processor.js";
import {attributes, rawAttributes} from "./helper.js";

function variableStatement(rawAttributes, context, html) {

    let attribute = rawAttributes.find((attribute) => attribute.startsWith("read:variable"))

    let variable = attribute.split("=")[1];

    let element = html.element;

    evaluation(variable + " = $value", context, {$value: element})

    return {
        build(parent) {
            return html.build(parent)
        },
        import(parent) {
            return variableStatement(rawAttributes, context, html)
                .build(parent)
        }
    }

}

export default customPlugins.define({
    name: ["read:variable"],
    destination: "Attribute",
    code: function (tagName, node, children, intern, isSvg, tabs, level) {
        return `\n${tabs}variableStatement([${rawAttributes(node)}], context, html("${tagName}", [${attributes(node)}], [${intern(node.childNodes, ++level, isSvg)}\n${tabs}]))`;
    },
    executor: variableStatement
})