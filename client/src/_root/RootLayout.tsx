import { Navigate, Outlet } from "react-router-dom"
import Menu from "../components/Menu"
import Logo from "../components/Logo"
import NewFriends from "../components/NewFriends"
import SearchBar from "../components/SearchBar"
import Greeting from "../components/Greeting"
import profile from "../assets/profile.png"

const RootLayout = () => {

  const isAuthenticated = true
  return (
    <>
      {
        isAuthenticated?
        (
          <section>
            <header>
              <Logo style='horizontal-logo' Name='Expium'/> 
              <SearchBar/>
              <Greeting MyName="Erik" MyPfp={profile}/>
            </header>
            <div className="layout">
              
              <div className="left-side">
                <Menu />
              </div>
              
              <Outlet />
              <div className="right-side">
                
                <NewFriends />
              </div>
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
