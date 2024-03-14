import { Link } from "react-router-dom"
import { IoIosArrowForward } from "react-icons/io";
import { UserContext } from "../contexts/UserContext";
import { useContext } from "react";
import { ProfilePicContext } from "../contexts/ProfilePicContext";


const Activity = (props:any) => {
  const { user } = useContext(UserContext);
  const { profilePic } = useContext(ProfilePicContext);
  return (
    <div>
        <Link to='/' className="activity-style">
            <div className="left">
                {/* <img src={(user != null && user.picture)? ("data:image/png;base64,"+user.picture):("data:image/png;base64,"+profilePic)}  alt="profile" className="profile" /> */}
                <div className="notify-text">
                    <h4> {props.username}</h4>
                    <p> {props.status}</p>
                    <p className="ago"> {props.time}</p>
                    <p> ago</p>
                </div>
            </div>
            <IoIosArrowForward className="arrow"/>
        </Link>
    </div>
  )
}

export default Activity
