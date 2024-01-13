import Editor from "@monaco-editor/react"
import './App.css'

import {useState, useEffect, useRef} from 'react'
import {text} from './examplecode.js'
import Tutorial from "./Tutorial.jsx";

const api_endpoint = "https://api.lambo.main.marvinmielchen.com"

function App() {

    const [statusOk, setStatusOk] = useState(false);

    useEffect(() => {
        fetch(api_endpoint + "/api/status").then((response) => {
            if (response.status !== 200) {
                setStatusOk(false);
            }else{
                setStatusOk(true);
            }
        });
    }, []);

    if(window.innerWidth <= 768){
        return (
            <div>
                <h2>Die Website ist für die mobile Nutzung noch nicht optimiert</h2>
            </div>
        );
    }

    if (statusOk) {
        return <MainPage />;
    } else return (
        <div className="App">
            <h1>Der Server ist gerade nicht erreichbar</h1>
        </div>
    );
}
function MainPage() {
    const editorRef = useRef(null)
    const [content, setContent] = useState(() => {
        const content = window.sessionStorage.getItem("content")
        return content || text
    })

    //reload on resize
    useEffect(() => {
        window.addEventListener("resize", () => {
            window.location.reload()
        })
    }, [])

    useEffect(() => {
        window.sessionStorage.setItem("content", String(content))
    }, [content])

    useEffect(() => {
        const content = window.sessionStorage.getItem("content")
        setContent(content)
    }, [])

    const handleReset = () => {
        displayErrors({})
        setContent(text)
    }

    const handleBetaReduction = () => {
        const payload = window.sessionStorage.getItem("content")
        fetch(api_endpoint + "/api/reduction", {
            method: "POST",
            headers: {
                "Content-Type": "text/plain",
            },
            body: payload,
        })
            .then((response) => response.json())
            .then((data) => {
                displayErrors(data)
                if(data.result){
                    setContent(data.result)
                }
            })
            .catch((error) => {
                console.error("Error:", error)
            })
    }

    const handleSyntaxCheck = (value) => {
        setContent(value)
        const payload = window.sessionStorage.getItem("content")
        fetch(api_endpoint + "/api/syntax", {
            method: "POST",
            headers: {
                "Content-Type": "text/plain",
            },
            body: value,
        })
            .then((response) => response.json())
            .then((data) => {
                displayErrors(data)
            })
            .catch((error) => {
                console.error("Error:", error)
            })
    }

    const handleDefinitionSubstitution = () => {
        const payload = window.sessionStorage.getItem("content")
        fetch(api_endpoint + "/api/substitution", {
            method: "POST",
            headers: {
                "Content-Type": "text/plain",
            },
            body: payload,
        })
            .then((response) => response.json())
            .then((data) => {
                displayErrors(data)
                if(data.result){
                    setContent(data.result)
                }
            })
            .catch((error) => {
                console.error("Error:", error)
            })
    }

    const displayErrors = (data) => {
        if (data.error) {
            const marker = {
                severity: monaco.MarkerSeverity.Error,
                message: data.error.message,
                startLineNumber: data.error.line,
                startColumn: 1,
                endLineNumber: data.error.line,
                endColumn: 100,
            }
            const markers = [marker]
            monaco.editor.setModelMarkers(editorRef.current.getModel(), "test", markers)
        }else{
            const markers = []
            monaco.editor.setModelMarkers(editorRef.current.getModel(), "test", markers)
        }
    }

    return (
        <div className="App">
            <div className="sidebar">
                <Tutorial />
            </div>
            <div className="content">
                <div className="toolbar">
                    <button onClick={handleDefinitionSubstitution}>Definitionen-Substitution</button>
                    <button onClick={handleBetaReduction}>Beta-Reduktion</button>
                    <button onClick={handleReset}>Zurücksetzen</button>
                </div>
                <Editor
                    height="100vh"
                    width="100%"
                    theme="vs-dark"
                    defaultLanguage="text"
                    value={String(content)}
                    onChange={handleSyntaxCheck}
                    onMount={(editor, _) => {
                        editorRef.current = editor
                    }}
                />
            </div>
        </div>
    );
}

export default App
