import Wrapper from "../assets/wrappers/Navbar";
import { FaAlignLeft } from "react-icons/fa";
import Logo from "./Logo";
import LogoutContainer from "./LogoutContainer";
import { useDashboardContext } from "../pages/DashboardLayout";
import ThemeToggle from "./ThemeToggle";

const Navbar = () => {
  const { toggleSidebar, user } = useDashboardContext();

  if (!user) {
    return null;
  }

  return (
    <Wrapper>
      <div className="nav-center">
        <button 
          type="button" 
          className="toggle-btn" 
          onClick={toggleSidebar}
          aria-label="Toggle Sidebar"
        >
          <FaAlignLeft />
        </button>
        <div className="logo-container">
          <Logo />
          <h4 className="logo-text">dashboard</h4>
        </div>
        <div className="btn-container">
          <ThemeToggle />
          <LogoutContainer />
        </div>
      </div>
    </Wrapper>
  );
};

export default Navbar;
