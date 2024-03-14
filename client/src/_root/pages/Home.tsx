import { useContext, useEffect, useState } from "react"

import Post from "../../components/Post"
import preloadPosts from "../../assets/preloadPosts.png"
import { Link } from "react-router-dom"
import CreatePost from "./CreatePost"

import axios from "axios"
import { set } from "react-hook-form"
import { ProfilePicContext } from "../../contexts/ProfilePicContext"

interface Post {
  id: number;
  content: string;
}

const Home = () => {

  const { profilePic } = useContext(ProfilePicContext);


  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [posts, setPosts] = useState<Post[]>([]);

  const params = {
    username: "ayoub",

  }

  interface Picture {
    id: string;
    type: string;
    picture: string;
  }


  const [Picture, setPicture] = useState<Picture>();

  useEffect(() => {
    axios
      .get(`http://localhost:8080/user/post/followingPosts?username=${localStorage.getItem("myUserName") }`, {
        
        headers: {
          Authorization: `Bearer ${localStorage.getItem("myToken")}`
        },
      })
      .then((res) => {
        setPosts(res.data);
        console.log(res.data);
        setPicture(res.data.postImage)
      })
      .catch((err) => {
        setError(err);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  // if (error) {
  //   return <div>There are no posts yet!{error.message}</div>;
  // }

  return (
    <div className="center content-area">
      <div className="linkto">
        <Link to="/add-post" className="btn-primary merge">Create new post</Link>
        
      </div>
      {isLoading? (<img src={preloadPosts}/>):("")}
      {error? (<div className="empty-page">There are no posts yet click above to create one!</div>):(posts.map((post) => (
        <Post
          profilePic={(post.profilePicture)?(post.profilePicture):(profilePic)}
          firstName={post.name}
          lastName=""
          ago={post.timeAgo}
          username={post.username}
          caption={post.content}
          isImage={(post.postImage)? "yes":"no"}
          
          thisImage={post.postImage}
        />
      )))}

    </div>
  )
}

export default Home
