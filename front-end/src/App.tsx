import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './styles/App.css'
import Mapbox from './MapBox'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div>
        <Mapbox></Mapbox>
      </div>
    </>
  )
}

export default App
