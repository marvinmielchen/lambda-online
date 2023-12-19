import Editor from "@monaco-editor/react"
import './App.css'

import { useState, useEffect } from 'react'
import {text} from './examplecode.js'

function App() {
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
        console.log("reset")
        setContent(text)
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
                    <button>Beta-Reduktion</button>
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
