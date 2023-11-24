import Post from "../../components/Post"
import profile from "../../assets/profile.png"
import Mypic from "../../assets/Mypic.jpg"
import bg from "../../assets/bg.jpg"
import { Link } from "react-router-dom"
import CreatePost from "./CreatePost"

const Home = () => {
  return (
    <div className="center">
      <div className="linkto">
        <Link to="/post" className="btn-primary merge">Create new post</Link>
        
      </div>
      <Post username="alhassan.zakriti" firstName="Al Hassan" lastName="Zakriti" profile={Mypic} caption="Hey everybody" isImage="yes" thisImage={Mypic}/>
      <Post username="ricard.the.only" firstName="Ricard" lastName="Alison" isImage="no" profile={profile} caption="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."/>
      <Post username="alexander" firstName="Alex" lastName="Samy" profile={bg} caption="The CURE is the best band ever, change my mind" isImage="yes" thisImage={bg}/>
    </div>
  )
}

export default Home
