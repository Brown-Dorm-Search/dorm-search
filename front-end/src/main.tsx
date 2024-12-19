import { StrictMode } from 'react'
import { BrowserRouter, Routes } from "react-router";
import { createRoot } from 'react-dom/client'
import './styles/index.css'
import App from './App.tsx'
import { Route } from 'react-router';
import Navbar from './Navbar.tsx';
import About from './About.tsx';
import Dropdowns from './Dropdowns.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
)