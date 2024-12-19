import './styles/App.css'
import './styles/Signin.css'
import { BrowserRouter, Routes, Route } from 'react-router'
import About from './About'
import Home from './Home'
import Navbar from './Navbar'
import {
  ClerkProvider,
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
    <ClerkProvider publishableKey={PUBLISHABLE_KEY} afterSignOutUrl="/">
      <div>
        <SignedOut>
          <div className="back" >
            <div className="container">
              <h1 className="welcome-text">Searh For Dorms At Brown University</h1>
              <p className="subtitle">A complete encyclopedia of Brown University dormitories for students to find their perfect match! We have created a secure website for Brown students to filter through the thousands of dorm rooms on campus in order to be prepared for the housing lottery and have all their dorm info in one place. </p>
              <SignInButton className="cta-button">Sign In</SignInButton>
            </div>
          </div>
        </SignedOut><SignedIn>
          <div>
            <BrowserRouter>
              <div className="header-top">
                <div className="title">Brown Dorm Finder</div>
                <Navbar />
                <SignOutButton className="cta-button">Sign Out</SignOutButton>
              </div>
              <div className="content" >
                <Routes>
                  <Route path="/" element={<Home />} />
                  <Route path="/about" element={<About />} />
                  <Route path="/contact" element={"Contact Page"} />
                </Routes>

              </div>
            </BrowserRouter>
          </div>
        </SignedIn></div>
    </ClerkProvider>
  )
}

export default App


