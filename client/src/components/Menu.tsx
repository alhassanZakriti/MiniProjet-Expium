import { Link, useLocation } from "react-router-dom";
import { GoHomeFill } from "react-icons/go";
import { BiSolidNotification } from "react-icons/bi";
import { BiSolidPlusSquare } from "react-icons/bi";
import { MdOutlineChatBubble } from "react-icons/md";
import { FaUserFriends } from "react-icons/fa";
import { IoMdPerson } from "react-icons/io";



const Menu = () => {
  const {pathname} = useLocation();
  
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
      <Link className="flexing" to='/post'>
        <BiSolidPlusSquare className={pathname === "/post"? "icon-menu-checked": "icon-menu-unchecked"}/>
        <h3 className={pathname === "/post"? "checked": "unchecked"}>Post</h3>
      </Link>
      <Link className="flexing" to='/chat'>
        <MdOutlineChatBubble className={(pathname === (("/chat")) || (pathname ===  ("/chat/message")))? "icon-menu-checked": "icon-menu-unchecked"}/>

        <h3 className={(pathname === (("/chat")) || (pathname ===  ("/chat/message")))? "checked": "unchecked"}>Chat</h3>
      </Link>
      <Link className="flexing" to='/friends'>
        <FaUserFriends className={pathname === "/friends"? "icon-menu-checked": "icon-menu-unchecked"}/>


        <h3 className={pathname === "/friends"? "checked": "unchecked"}>Friends</h3>
      </Link>
      <Link className="profile-hider flexing" to='/profile'>
        <IoMdPerson className={pathname === "/profile"? "icon-menu-checked ": "icon-menu-unchecked "}/>

        <h3 className={pathname === "/profile"? "checked ": "unchecked "}>Profile</h3>
      </Link>

    </div>
  )
}

export default Menu
