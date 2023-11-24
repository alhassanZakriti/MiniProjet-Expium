import Friend from "../../components/Friend"
import profile from "../../assets/profile.png"

const Friends = () => {
  return (
    <div className="all-friends">
      <Friend profile={profile} userName="as32.ed" firstName="Salvador" lastName="Sebastian" />
      
    </div>
  )
}

export default Friends
