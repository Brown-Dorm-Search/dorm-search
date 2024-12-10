import { StrictMode } from 'react'
import { BrowserRouter, Routes } from "react-router";
import { createRoot } from 'react-dom/client'
import './styles/index.css'
import App from './App.tsx'
import { Route } from 'react-router';
import Navbar from './Navbar.tsx';

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <Navbar />
    <div className="content">
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/about" element={"About Page"} />
        <Route path="/contact" element={"Contact Page"} />
      </Routes>
    </div>
  {/* <StrictMode>
    <App />
  </StrictMode> */}
  </BrowserRouter>,
)
