import { useState } from 'react'
import reactLogo from './assets/react.svg'
import Editor from "@monaco-editor/react"
import './App.css'


function App(){
    const [count, setCount] = useState(0)

    // it will take up the full width / height of its container
    //editor of any size


    return (
        <div className="App">
            <Editor
                height="100vh"
                width="100%"
                theme="vs-dark"
                defaultLanguage="text"
            />
        </div>
    )
}
export default App
