import { useState } from "react"
import { Link } from "react-router-dom"
import { IoIosArrowUp } from "react-icons/io";
import { MdOutlineChatBubble } from "react-icons/md";
import { FaBookmark } from "react-icons/fa6";
import { MdOutlineMoreHoriz } from "react-icons/md";

import OutsideClickHandler from 'react-outside-click-handler';

import onClickOutside from 'react-onclickoutside';



const Post = (props:any) => {


    
    
    const [isUp,setIsUp]= useState(false);
    const [isSaved,setIsSaved]= useState(false);
    const [showPopUp, setShowPopUp] = useState(false);
    
    // if (props.saved === "yes") {
    //     setIsSaved(true);
    // } else {
    //     setIsSaved(false);
    // }

    
    const handleClickOutside = () => setShowPopUp(false);

   
  return (
    <div className="post-style">
        <div className="top-post">
            <Link to="/profile" className="flex">
                <img className="profile-pic" src={props.profile} alt="profile" />
                <div className="name-user">
                    <h2 className="name-area">{props.firstName} {props.lastName}</h2>
                    <p className="user-area">@{props.username}</p>
                </div>
            </Link>
            <OutsideClickHandler onOutsideClick={() => setShowPopUp(false)}> <button className="three-points" onClick={()=>setShowPopUp(!showPopUp)}><MdOutlineMoreHoriz /></button></OutsideClickHandler>
            {(showPopUp)? (
                <div className="back-pop">
                    <div className="pop-up">
                        <button className="edit">Edit</button>
                        <button className="hide">Hide</button>
                        <button className="delete">Delete</button>
                    </div>
                </div>
            ) : ""  }
        </div>
        <div className="post-content">
            {props.caption}
            {(props.isImage ==="yes")? <img src={props.thisImage} className="post-picture"/> : ""}
            
        </div>
        <div className="reactions">
            <div className="upVote-btn" onClick={()=>setIsUp(!isUp)}>
                <IoIosArrowUp className={(isUp)? "upVoted space":"upVote space"}/>
                {(isUp)? (<h4 className="upVoted">Upvoted</h4>):(<h4 className="UpVote">Upvote</h4>)}
            </div>
            <Link to="/post" className="upVote-btn">
                <MdOutlineChatBubble className="upVote space"/>
                <h4 className="upVote">Comment</h4>
            </Link>
            <div className="upVote-btn" onClick={()=>setIsSaved(!isSaved)}>
                <FaBookmark className={(isSaved)? "upVoted space":"upVote space"}/>
                {(isSaved)? (<h4 className="upVoted">Saved</h4>):(<h4 className="UpVote">save</h4>)}
            </div>
        </div>
    </div>
  )
}


export default Post
