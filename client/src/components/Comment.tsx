import React, { useContext } from 'react'
import { UserContext } from '../contexts/UserContext';
import { ProfilePicContext } from '../contexts/ProfilePicContext';

const Comment = () => {
    const { user } = useContext(UserContext);
    const { profilePic } = useContext(ProfilePicContext);

  return (
    <div className='comment-wrapper'>
        <div className='head-comment'>
            <img src={("data:image/png;base64,"+profilePic)} alt="profile picture" />
            <div className="user-info">
                <h2>{user!= null? (user.name):""}</h2>
            </div>
        </div>
    </div>
  )
}

export default Comment
