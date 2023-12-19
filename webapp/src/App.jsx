import Editor from "@monaco-editor/react"
import './App.css'

import { useState, useEffect } from 'react'
import {text} from './examplecode.js'


function App() {
    const [statusOk, setStatusOk] = useState(false);

    useEffect(() => {
        fetch("http://localhost:8080/api/status").then((response) => {
            if (response.status !== 200) {
                setStatusOk(false);
            }else{
                setStatusOk(true);
            }
        });
    }, []);

    if (statusOk) {
        return <MainPage />;
    } else return (
        <div className="App">
            <h1>Der Server ist gerade nicht erreichbar</h1>
        </div>
    );
}
function MainPage() {
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

    const handleEditorChange = (value) => {
        setContent(value)
    }

    const handleReset = () => {
        setContent(text)
    }

    const handleBetaReduction = () => {
        const payload = window.sessionStorage.getItem("content")
        fetch("http://localhost:8080/api/reduction", {
            method: "POST",
            headers: {
                "Content-Type": "text/plain",
            },
            body: payload,
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data)
            })
            .catch((error) => {
                console.error("Error:", error)
            })
    }

    return (
        <div className="App">
            <div className="sidebar">
                <ul>
                    <li><a href="#"></a></li>
                    <li><a href="#"></a></li>
                    <li><a href="#"></a></li>
                </ul>
            </div>
            <div className="content">
                <div className="toolbar">
                    <button>Definitionen-Substitution</button>
                    <button onClick={handleBetaReduction}>Beta-Reduktion</button>
                    <button onClick={handleReset}>Zurücksetzen</button>
                </div>
                <Editor
                    height="100vh"
                    width="100%"
                    theme="vs-dark"
                    defaultLanguage="text"
                    value={String(content)}
                    onChange={handleEditorChange}
                />
            </div>
        </div>
    );
}

export default App
