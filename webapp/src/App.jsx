import { useState } from 'react'
import Editor from "@monaco-editor/react"
import './App.css'


function App() {
    const [count, setCount] = useState(0);

    return (
        <div className="App">
            <div className="sidebar">
                <ul>
                    <li><a href="#">Link 1</a></li>
                    <li><a href="#">Link 2</a></li>
                    <li><a href="#">Link 3</a></li>
                </ul>
            </div>
            <div className="content">
                <div className="buttons">
                    <button>Definitionen einsetzen</button>
                    <button>Beta-Reduktion</button>
                </div>
                <Editor
                    height="100vh"
                    width="100%"
                    theme="vs-dark"
                    defaultLanguage="text"
                />
            </div>
        </div>
    );
}

export default App
