import { SetStateAction, useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './styles/App.css'
import Dropdowns from './Dropdowns'
import { BrowserRouter, Routes, Route } from 'react-router'
import About from './About'
import Home from './Home'
import Navbar from './Navbar'


function App() {
  return (
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