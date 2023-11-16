import '../style/style.css'
import SigninForm from "./forms/SigninForm"
import { Outlet, Navigate } from "react-router-dom";
import { useState, useRef, useEffect } from "react";
import SignupForm from "./forms/SignupForm";
import heroPic from "../assets/heroPic.png"
import behind from "../assets/behindGrad.jpg"


const AuthLayout = () => {

  // const [signin, setSignin] = useState(false);
  const isAuthenticated = false;

  return (

    <>
      {
        isAuthenticated? (
          <Navigate to='/' />
        ):(
          <section className="landing-page">
            <img src={behind} alt="gradient" className="behind"/>
            <div className="split">
              <Outlet />
              <img src={heroPic} alt="hero-picture" className="hero-right"/>
            </div>
          </section>
        )
      }
    </>
    // <div className="landing-page">
    //   <img src={behind} alt="gradient" className="behind"/>
    //   <div className="split">
    //     <SigninForm/>
        
    //     <img src={heroPic} alt="hero-picture" className="hero-right"/>
    //   </div>
    // </div>
  )
}

export default AuthLayout
