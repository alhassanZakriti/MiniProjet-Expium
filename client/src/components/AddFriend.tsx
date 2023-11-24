import { Link } from "react-router-dom"

const AddFriend = (props:any) => {
  return (
    <div className="add-friend">
        <Link to="/profile" className="flex">
            <img className="profile-pic" src={props.profile} alt="profile" />
            <div className="name-user">
              <h2 className="name-area">{props.firstName} {props.lastName}</h2>
              <p className="user-area">@{props.username}</p>
            </div>
        </Link>
        <Link to="/profile">
            
        </Link>
        <svg className="" width="14" height="14" viewBox="0 0 14 14" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd" d="M6 8V14H8V8H14V6H8V0H6V6H0V8H6Z" />
        </svg>

    </div>
  )
}

export default AddFriend
