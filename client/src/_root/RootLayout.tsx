import { Navigate, Outlet } from "react-router-dom"
import Menu from "../components/Menu"
import Logo from "../components/Logo"
import SearchBar from "../components/SearchBar"
import Greeting from "../components/Greeting"
import profile from "../assets/profile.png"
import background from "../assets/behindGrad.jpg"
import AddFriends from "../components/AddFriends"


const RootLayout = () => {

  const isAuthenticated = true
  return (
    <>
      {
        isAuthenticated?
        (
          
          <section>
            <header className="header">
              <Logo style='horizontal-logo' Name='Expium'/> 
              <SearchBar/>
              <Greeting MyName="Erik" MyPfp={profile}/>
            </header>
            <div className="layout">
              
              <div className="left-side">
                <Menu />
              </div>
              
              <Outlet />
              <AddFriends />
            </div>
          </section>
        ):(
          <Navigate to='/sign-in'/>
        )
      }
    </>
  )
}

export default RootLayout
