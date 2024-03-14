import React, { useEffect, useState } from 'react'

import Activity from "../../components/Activity"

import axios from "axios"

const Activities = () => {

  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [notifications, setNotifications] = useState<any>();

  const username = localStorage.getItem("myUserName");

  useEffect(() => {
    axios
      .get(`http://localhost:8080/user/notifications?username=${localStorage.getItem("myUserName") }`, {
          
      headers: {
        Authorization: `Bearer ${localStorage.getItem("myToken")}`
      },
      })
      .then((res) => {
        setNotifications(res.data);
        console.log(notifications);
      })
      .catch((err) => {
        setError(err);
        console.log(err);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }
  , []);
  
  return (
    <div className="center content-area">

      <h1>Activities</h1>


      
      {isLoading? <h1>Loading...</h1> : 
        <div>
          {notifications.map((notification:any) => {
            return(
              <Activity username={notification.title} time={notification.timeAgo} status={notification.content} />
            )
          })}
        </div>
      }
      {/* <Activity profile={profile} username="lampard.stan" status="upvote"/>
      <Activity profile={bg} username="the.cure" status="no"/>
      <Activity profile={Mypic} username="alhassan.zakriti" status="no"/> */}

    </div>
  )
}

export default Activities
