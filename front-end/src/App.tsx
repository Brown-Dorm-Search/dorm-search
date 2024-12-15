import { SetStateAction, useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './styles/App.css'
import Mapbox from './MapBox'
import Dropdowns from './Dropdowns'
import { BrowserRouter, Routes, Route } from 'react-router'
import About from './About'
import Home from './Home'
import Navbar from './Navbar'


function App() {  //used prop.clickedDorm[] to get clicked dorm
  const [filteredDorms, setFilteredDorms] = useState<Array<string>>([]);
  const [clickedDorm, setClickedDorms] = useState<string>('');
  //set filtered dorms to change map highlights.
  return (
    <BrowserRouter>
      <div className="header-top">
        <div className="title">Brown Dorm Search</div>
        <Navbar />
      </div>

      <div className="content">
        <Routes>
          <Route path="/" element={<Home />} />
          <Mapbox filteredDorms={filteredDorms} setFilteredDorms={setFilteredDorms}
            clickedDorm={clickedDorm} setClickedDorm={setClickedDorms}></Mapbox>
          <Route path="/about" element={<About />} />
          <Route path="/contact" element={"Contact Page"} />
        </Routes>
      </div>
    </BrowserRouter>
  )
}

export default App


{/* 
  <BrowserRouter>
    <div className="header-top">
      <div className="title">Brown Dorm Search</div>
      <Navbar />
    </div>
  
    <div className="content">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={"Contact Page"} />
      </Routes>
    </div>
  </BrowserRouter> */}