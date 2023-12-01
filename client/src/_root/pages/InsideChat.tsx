import { Link } from "react-router-dom";
import { IoIosArrowBack } from "react-icons/io";
import profile from '../../assets/profile.png'

const InsideChat = () => {

  const data :{firstname: String; lastname: String ; username: String}={
    firstname: "Al Hassan",
    lastname: "Zakriti",
    username: "alhassan.zakriti"

  };

  const message: {recieved: String; sent: String;  sentShort: String}={
    recieved: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam sed urna ac tortor semper semper. Donec laoreet, massa eget semper laoreet, ipsum eros auctor tortor.",
    sent: "Sed non eros at lorem dignissim semper. Fusce ac metus vel purus semper laoreet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae.",
    sentShort: "et ultrices posuere cubilia curae."
  }

  return (
    <div className="layout-chat">
      <div className="head-chat flex">
        <Link to='/chat' className="overIcon">
          <IoIosArrowBack className="icon"/>
        </Link>
        <Link to='/profile' className="flex uncolor">
          <img src={profile} className="profile-pic"/>
          <h2 className="fullname">{data.firstname} {data.lastname}</h2>
        </Link>
      </div>
      <div className="messages">
        <p className="recieved">
          {message.recieved}
        </p>
        <p className="sent">
          {message.sent}
        </p>
        <p className="recieved">
          {message.recieved}
        </p>
        <p className="recieved">
          {message.recieved}
        </p>
        <p className="sent">
          {message.sentShort}
        </p>
        <p className="sent">
          {message.sentShort}
        </p>
        <p className="sent">
          {message.sentShort}
        </p>
      </div>
      <form className="foot-chat">
        <input placeholder="type something..." type="text" name="message"  />
        <button type="submit">Send</button>
      </form>
    </div>
  )
}

export default InsideChat
