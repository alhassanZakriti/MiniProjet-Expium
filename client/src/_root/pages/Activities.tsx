import Activity from "../../components/Activity"
import profile from '../../assets/profile.png'
import Mypic from '../../assets/Mypic.jpg'
import bg from '../../assets/bg.jpg'

const Activities = () => {
  return (
    <div className="center content-area">
      <h1>Activities</h1>
      <Activity profile={profile} username="lampard.stan" status="upvote"/>
      <Activity profile={bg} username="the.cure" status="no"/>
      <Activity profile={Mypic} username="alhassan.zakriti" status="no"/>

    </div>
  )
}

export default Activities
