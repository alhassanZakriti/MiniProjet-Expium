import AddFriend from "./AddFriend"
import { useState, useRef,useEffect } from "react";
import profile from "../assets/profile.png"
import axios from "axios";


const AddFriends = () => {

  const [users, setUsers] = useState<User[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/user/suggestFriends?username=${localStorage.getItem("myUserName")}`,{
        headers: {
          Authorization: `Bearer ${localStorage.getItem("myToken")}`,
        },
      })
      .then((res) => {
        setUsers(res.data);
        console.log(res.data);
      })
      .catch((err) => {
        setError(err);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  console.log(users);

  return (
    <div className="right-side">
        <h1>Add New Friends</h1>
        {isLoading? (<div className="empty-page">Loading...</div>):("")}
        {error? (<div className="empty-page">There are no people to find!</div>):(users.map((user) => (
        <AddFriend profile={profile} username={user.username} firstName={user.name} lastName="" />
      )))}
      
    </div>
  )
}

export default AddFriends
