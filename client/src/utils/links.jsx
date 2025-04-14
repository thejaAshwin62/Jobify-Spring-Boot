import { FaHome, FaUserCircle, FaCalendarAlt, FaWpforms } from "react-icons/fa";
import { MdAdminPanelSettings } from "react-icons/md";

const links = [
  {
    text: "add job",
    path: ".",
    icon: <FaHome />,
  },
  {
    text: "all jobs",
    path: "all-jobs",
    icon: <FaCalendarAlt />,
  },
  {
    text: "stats",
    path: "stats",
    icon: <FaWpforms />,
  },
  {
    text: "profile",
    path: "profile",
    icon: <FaUserCircle />,
  },
  {
    text: "admin",
    path: "admin",
    icon: <MdAdminPanelSettings />,
  },
];

export default links;
