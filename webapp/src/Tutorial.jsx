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

Definitionen weisen einem Bezeichner einen Lambda Ausdruck zu, also entweder eine Variable eine Abstraktion oder eine Applikation:
\`\`\`
def variable x

def abstraktion(x){
    x
}

def applikation(x){
    {x x}
}
\`\`\`

Um komplexe Ausdrücke zu definieren können Lambda Ausdrücke auch ineinander verschachtelt werden:
\`\`\`
def complex(x){
    (y){
        {x {y y}}
    }
}
\`\`\`

Wenn mehrere Definitionen in einem Programm programmiert werden, so lassen sich diese auch gegenseitig referenzieren:
\`\`\`
def a 1
def b 2
def result {a b}
\`\`\`

Um ein Programm auszuführen kann etweder eine Definitionen-Substitution oder eine Beta-Reduktion durchgeführt werden:
- Die Definitionen-Substitution ersetzt alle Vorkommen eines Bezeichners durch den Lambda Ausdruck, der diesem Bezeichner zugewiesen wurde.
- Die Beta-Reduktion versucht eine reduzierbare Applikation zu reduzieren.

Bei Programmieren muss allerdings beachtet werden dass die Reihenfolge der Definitionen insoweit relevant ist, dass eine Definition zu einem Bezeichner auswertet wenn dieser zuvor definiert wurde.

### Dann mal los!

-------------------------
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

