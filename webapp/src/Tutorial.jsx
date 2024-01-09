import "./Tutorial.css";
import Markdown from "react-markdown";


const markdown =
`
# Lambo
Willkommen im Online Interpreter der Programmiersprache Lambo. Lambo ist eine minimalistische Sprache, die auf den Prinzipien des Lambda-Kalküls basiert.

### Syntax
Es gibt nur eine Art von Statement in Lambo uns das ist die Definition die mit dem Schlüsselwort \`def\` eingeleitet wird:
\`\`\`
def x 5
\`\`\`

Definition weisen einem Bezeichner einen Lambda Ausdruck zu, also entweder eine Variable eine Abstraktion oder eine Applikation:
\`\`\`
def variable x

def abstraktion (x) {
    x
}

def applikation (x) {
    {x x}
}
\`\`\`
---
Diese Website präsentiert das Ergebnis einer Seminararbeit von Marvin Mielchen aus dem Seminar „Funktionale Programmierung“ an der Universität Hamburg.
`

function Tutorial() {

    return (
        <div className="tutorial">
            <Markdown>
                {markdown}
            </Markdown>
        </div>
    );
}

export default Tutorial;

