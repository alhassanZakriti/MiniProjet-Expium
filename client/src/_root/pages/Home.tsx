import { useEffect, useState } from "react"

import Post from "../../components/Post"
import profile from "../../assets/profile.png"
import Mypic from "../../assets/Mypic.jpg"
import bg from "../../assets/bg.jpg"
import { Link } from "react-router-dom"
import CreatePost from "./CreatePost"

import axios from "axios"

const Home = () => {

  const [users, setUsers] = useState<User[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("http://localhost:8080/user/post/for-friends")
      .then((res) => {
        setUsers(res.data);
      })
      .catch((err) => {
        setError(err);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  console.log(users);
  // if (error) {
  //   return <div>There are no posts yet!{error.message}</div>;
  // }

  return (
    <div className="center content-area">
      <div className="linkto">
        <Link to="/add-post" className="btn-primary merge">Create new post</Link>
        
      </div>
      
      {error? (<div className="empty-page">There are no posts yet click above to create one!</div>):(users.map((user) => (
        <Post
          // key={user.id} // Assuming each user has a unique ID
          profile={profile}
          firstName={user.name}
          lastName=""
          username={user.username}
          caption="This is a post"
          isImage="no"
        />
      )))}

    </div>
  )
}

export default Home
