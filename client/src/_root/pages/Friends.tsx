import Friend from "../../components/Friend"
import profile from "../../assets/profile.png"
import { useEffect, useState } from "react";
import axios from "axios";

const Friends = () => {


  const [friends, setFriends] = useState([]);
  const [picture, setPicture] = useState("");
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/user/friends?username=${localStorage.getItem("myUserName") }`, {
        
        headers: {
          Authorization: `Bearer ${localStorage.getItem("myToken")}`
        },
      })
      .then((res) => {
        setFriends(res.data);
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

  return (
    <div className="all-friends content-area">

      {isLoading && <p>Loading...</p>}
      {error && <p>Error: {error}</p>}
      {friends.map((friend: any) => (
        <Friend profile={profile} userName={friend.username} firstName={friend.name}  />
      ))}
      
      
    </div>
  )
}

export default Friends
