import { Link } from "react-router-dom"
import { MdOutlineChatBubble } from "react-icons/md";
import { IoIosArrowForward } from "react-icons/io";



const Friend = (props:any) => {
  return (
    <div className="friend-box">
        <img src={props.profile} alt="profile-pic" />
        <h3>{props.firstName} {props.lastName}</h3>
        <Link className="linkto-profile" to="/profile">@{props.userName}</Link>
        <div className="actions">
            <button>Unfollow</button>
            <Link className="chat" to="/chat/message">
                <h4>DM</h4>
                <MdOutlineChatBubble className="chat-bubble"/>
            </Link>
        </div>
        <Link className="linkto-profile" to="/profile">
            View profile 
            <IoIosArrowForward />
        </Link>
    </div>
  )
}

export default Friend
