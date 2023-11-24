import { Link } from "react-router-dom"
import { IoIosArrowForward } from "react-icons/io";

const Recent = (props:any) => {
  return (
    <div>
        <Link to='/chat/message' className="activity-style">
            <div className="left">
                <img src={props.profile} alt="profile" className="profile" />
                <div className="message-area">
                    <h4>{props.firstName} {props.lastName}</h4>
                    <p>{props.lastMessage}</p>
                </div>
            </div>
            <IoIosArrowForward className="arrow"/>
        </Link>
    </div>
  )
}

export default Recent
