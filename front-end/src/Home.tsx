import { SetStateAction, useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './styles/App.css'
import './styles/Home.css'
import Dropdowns from './Dropdowns'
import MapBox from './MapBox'

function Home() {
  const [count, setCount] = useState(0)
  const [resultString, setResultString] = useState('');


  return (
    <>
      <div>
        <div className="header-bottom">
          <Dropdowns resultStr={resultString} setResultStr={setResultString}></Dropdowns> 
        </div>
        <div className="panel-container">
            <div className="panel left">Left Panel</div>
            <div className="panel center"><MapBox></MapBox></div>
            <div className="panel right">{resultString}</div>
         </div>
      </div>
    </>
  )
}

export default Home