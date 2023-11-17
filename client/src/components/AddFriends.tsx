import AddFriend from "./AddFriend"
import profile from "../assets/profile.png"

const AddFriends = () => {
  return (
    <div className="right-side">
        <h1>Add New Friends</h1>
      <AddFriend profile={profile} username="alhassan.zakriti" firstName="Al Hassan" lastName="Zakriti" />
      <AddFriend profile={profile} username="alhassan.zakriti" firstName="Al Hassan" lastName="Zakriti" />
      <AddFriend profile={profile} username="alhassan.zakriti" firstName="Al Hassan" lastName="Zakriti" />
      <AddFriend profile={profile} username="alhassan.zakriti" firstName="Al Hassan" lastName="Zakriti" />
    </div>
  )
}

export default AddFriends
