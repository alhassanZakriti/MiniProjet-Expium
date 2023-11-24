import Recent from "../../components/Recent"
import profile from "../../assets/profile.png"

const Chat = () => {
  return (
    <div>
      <Recent profile={profile} firstName="Lincon" lastName="Archangel" lastMessage="Hey how you doing mate where have you been all these days?"/>
    </div>
  )
}

export default Chat
