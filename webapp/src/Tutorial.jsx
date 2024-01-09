import "./Tutorial.css";
import Markdown from "react-markdown";


const markdown =
`
# Lambo
Willkommen im Online Interpreter der Programmiersprache Lambo. Lambo ist eine minimalistische Sprache, die auf den Prinzipien des Lambda-Kalküls basiert.

Diese Website ist das Ergebnis einer Seminararbeit an der Universität Hamburg.

Beginnen Sie mit dem Schreiben von Lambo-Code im Hauptfenster und nutzen Sie die Auswertungsfunktionen, um das Ergebnis zu sehen. Experimentieren Sie mit verschiedenen Funktionen und Parametern, um ein tieferes Verständnis des Lambda-Kalküls zu erlangen.

## Syntax
Die Syntax von Lambo ist sehr einfach. Es gibt nur drei Arten von Ausdrücken:

- Variablen
- Funktionen
- Anwendungen

### Variablen
Variablen sind Zeichenketten, die mit einem Kleinbuchstaben beginnen. Sie können Buchstaben, Zahlen und Unterstriche enthalten.

### Code
\`\`\`
x
y
z
\`\`\`
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

