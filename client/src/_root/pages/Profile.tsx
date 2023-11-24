import { Link } from 'react-router-dom'
import profile from '../../assets/profile.png'
import Mypic from "../../assets/Mypic.jpg"
import bg from "../../assets/bg.jpg"
import Post from '../../components/Post'

const Profile = () => {
  let firstname="Erik"
  let lastname="Garcia"
  let username="erik.garcia"
  return (
    <div className='profile'>
      <div className="pic-name">
        <div className="img-follow">
          <img className='profile-picture' src={profile} alt="porfile-picture" />
          <Link to="/settings" className='secondary-btn'>Edit your profile</Link>
        </div>
        <div className="name-with-user">
           <div className="flex-name-follow">
            <div className="namer">
              <h3>{firstname} {lastname}</h3>
              <p className='user-area'>@{username}</p>
            </div>
           </div>
           <span>
           Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
           </span>

        </div>
        <div className="posts">
          <div className="btns">
            <button>My posts</button>
            <button>Saved</button>
          </div>
          <Post username={username} firstName={firstname} lastName={lastname} isImage="no" profile={profile} caption="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."/>
          <Post username={username} firstName={firstname} lastName={lastname} profile={profile} caption="Hey guys! how you doing hope y'all doing good see you soon guys!!" isImage="yes" thisImage={bg}/>
        </div>
      </div>
    </div>
  )
}

export default Profile
