import { useState } from 'react'
import Editor from "@monaco-editor/react"
import './App.css'


function App() {
    const [content, setContent] = useState("asdfasdf");

    //reload on resize


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
                    <button>Definitionen einsetzen</button>
                    <button>Beta-Reduktion</button>
                </div>
                <Editor
                    height="100vh"
                    width="100%"
                    theme="vs-dark"
                    defaultLanguage="text"
                    defaultValue={content}
                />
            </div>
        </div>
    );
}

export default App
