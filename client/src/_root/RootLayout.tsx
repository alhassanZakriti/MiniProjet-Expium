import { Navigate, Outlet } from "react-router-dom"
import Menu from "../components/Menu"
import Logo from "../components/Logo"
import SearchBar from "../components/SearchBar"
import Greeting from "../components/Greeting"
import AddFriends from "../components/AddFriends"


const RootLayout = () => {

  const token = localStorage.getItem('myToken');
  return (
    <>
      {
        (token)?
        (
          
          <section>
            <header className="header">
              <Logo style='horizontal-logo' Name='Expium'/> 
              <SearchBar/>
              <Greeting/>
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
