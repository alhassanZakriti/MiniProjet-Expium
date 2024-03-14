import { FaBookmark } from "react-icons/fa6"
import { IoIosArrowUp } from "react-icons/io"
import { MdOutlineChatBubble, MdOutlineMoreHoriz } from "react-icons/md"
import { Link, useParams } from "react-router-dom"
import {useState, useEffect} from "react";
import axios from "axios";

const InsidePost = (props:any) => {
  const postId = useParams();
  const postID =  postId.postId;

  console.log(postID);
    const [isUp,setIsUp]= useState(false);
    const [isSaved,setIsSaved]= useState(false);

    const [isLoading, setIsLoading] = useState(true);   
    const [error, setError] = useState(null);
    const [info, setInfo] = useState<any>();

    useEffect(() => {

        axios
        .get(`http://localhost:8080/user`, {
          
          headers: {
            Authorization: `Bearer ${localStorage.getItem("myToken")}`
          },
        })
        .then((res) => {
          setInfo(res.data);
          console.log(res.data);
        })
        .catch((err) => {
          setError(err);
        })
        .finally(() => {
          setIsLoading(false);
        });

    }
    , []);
    
  return (
    <div className="post-style content-area">
        <div className="top-post ">
            <Link to="/profile" className="flex">
                <img className="profile-pic" src={props.profile} alt="profile" />
                <div className="name-user">
                    <h2 className="name-area">{props.firstName} {props.lastName}</h2>
                    <p className="user-area">@{props.username}</p>
                </div>
            </Link>
            <MdOutlineMoreHoriz  className="three-points"/>
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
        <Comment />
    </div>
  )
}

export default InsidePost
