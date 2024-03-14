import { Link, useLocation } from "react-router-dom";
import { GoHomeFill } from "react-icons/go";
import { BiSolidNotification } from "react-icons/bi";
import { BiSolidPlusSquare } from "react-icons/bi";
import { MdOutlineChatBubble } from "react-icons/md";
import { FaUserFriends } from "react-icons/fa";
import { IoMdPerson } from "react-icons/io";
import axios from "axios";
import { IoLogOut } from "react-icons/io5";
import { useContext } from "react";
import { UserContext } from "../contexts/UserContext";



const Menu = () => {
  const {pathname} = useLocation();
  

  const username = localStorage.getItem("myUserName");
  const destroyToken = () => {
    localStorage.removeItem("myToken");
    localStorage.removeItem("myUserName");
    axios.post('http://localhost:8080/user/auth/logout', {username})
      .then(response => {
        console.log('logged out successfully:', response.data);
      })
      .catch(error => {
        console.error('Error creating user:', error);
      });
    window.location.href = "/sign-in";
  }

  return (
    <div className="menu">
      <Link className="flexing " to='/'>
        <GoHomeFill className={pathname === "/"? "icon-menu-checked": "icon-menu-unchecked"}/>

        <h3 className={pathname === "/"? "checked": "unchecked"}>Home</h3>
      </Link>
      <Link className="flexing" to='/activities'>
        <BiSolidNotification className={pathname === "/activities"? "icon-menu-checked": "icon-menu-unchecked"} />
        <h3 className={pathname === "/activities"? "checked": "unchecked"}>Activities</h3>
      </Link>
      <Link className="flexing" to='/add-post'>
        <BiSolidPlusSquare className={pathname === "/add-post"? "icon-menu-checked": "icon-menu-unchecked"}/>
        <h3 className={pathname === "/add-post"? "checked": "unchecked"}>Post</h3>
      </Link>
      <Link className="flexing" to='/chat'>
        <MdOutlineChatBubble className={(pathname === (("/chat")) || (pathname ===  ("/chat/message")))? "icon-menu-checked": "icon-menu-unchecked"}/>

        <h3 className={(pathname === (("/chat")) || (pathname ===  ("/chat/message")))? "checked": "unchecked"}>Chat</h3>
      </Link>
      <Link className="flexing" to='/friends'>
        <FaUserFriends className={pathname === "/friends"? "icon-menu-checked": "icon-menu-unchecked"}/>


        <h3 className={pathname === "/friends"? "checked": "unchecked"}>Friends</h3>
      </Link>
      <Link className="profile-hider flexing" to={`/profile/${username}`}>
        <IoMdPerson className={pathname === "/profile/*"? "icon-menu-checked ": "icon-menu-unchecked "}/>

        <h3 className={pathname === "/profile/*"? "checked ": "unchecked "}>Profile</h3>
      </Link>

      <button onClick={destroyToken} className="btn-log">
        <span>Log out</span>
        <IoLogOut />
      </button>

    </div>
  )
}

export default Menu
