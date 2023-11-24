import { Link } from "react-router-dom"
import { IoIosArrowForward } from "react-icons/io";


const Activity = (props:any) => {
  return (
    <div>
        <Link to='/post' className="activity-style">
            <div className="left">
                <img src={props.profile} alt="profile" className="profile" />
                <div className="notify-text">
                    <h4>{props.username}</h4>
                    <p>{(props.status)==="upvote" ? "Has upvoted your post": "Has commented on your post"}</p>
                </div>
            </div>
            <IoIosArrowForward className="arrow"/>
        </Link>
    </div>
  )
}

export default Activity
