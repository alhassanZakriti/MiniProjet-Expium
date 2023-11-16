import { Navigate, Outlet } from "react-router-dom"
import Menu from "../components/Menu"


const RootLayout = () => {

  const isAuthenticated = true
  return (
    <>
      {
        isAuthenticated?
        (
          <section>
            <div>
              <Menu />
              <Outlet />
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
