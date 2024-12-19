import { SetStateAction, useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './styles/App.css'
import Dropdowns from './Dropdowns'
import { BrowserRouter, Routes, Route } from 'react-router'
import About from './About'
import Home from './Home'
import Navbar from './Navbar'
import {
  SignedIn,
  SignedOut,
  SignInButton,
  SignOutButton,
  UserButton,
  useUser,
} from "@clerk/clerk-react";

const PUBLISHABLE_KEY = import.meta.env.VITE_CLERK_PUBLISHABLE_KEY

if (!PUBLISHABLE_KEY) {
  throw new Error("Missing Publishable Key")
}

function App() {
  return (
    <div><SignedOut>
      <div className="container">
        <h1 className="welcome-text">Welcome!</h1>
        <p className="subtitle">We are all about yes</p>
        <SignInButton className="cta-button">Sign In</SignInButton>
      </div>
    </SignedOut><SignedIn>
        <div>
          <BrowserRouter>
            <div className="header-top">
              <div className="title">Brown Dorm Finder</div>
              <Navbar />
            </div>
            <div className="content">
              <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/about" element={<About />} />
                <Route path="/contact" element={"Contact Page"} />
              </Routes>
              <SignOutButton aria-label="SignOut" />
            </div>
          </BrowserRouter>
        </div>
      </SignedIn></div>
  )
}

export default App


