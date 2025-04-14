import { NavLink } from "react-router-dom";
import { useDashboardContext } from "../pages/DashboardLayout";
import Wrapper from "../assets/wrappers/NavLinks";
import links from "../utils/links.jsx";

const NavLinks = ({ isBigSidebar }) => {
  const { toggleSidebar, user } = useDashboardContext();

  if (!user) {
    return null;
  }

  return (
    <Wrapper>
      <nav className="nav-links" aria-label="Main navigation">
        {links.map((link) => {
          const { text, path, icon } = link;
          const { role } = user;
          if (path === "admin" && role !== "ADMIN") return null;
          return (
            <NavLink
              to={path}
              key={text}
              className={({ isActive }) =>
                isActive ? "nav-link active" : "nav-link"
              }
              onClick={isBigSidebar ? null : toggleSidebar}
              end
            >
              <span className="icon" aria-hidden="true">{icon}</span>
              <span>{text}</span>
            </NavLink>
          );
        })}
      </nav>
    </Wrapper>
  );
};

export default NavLinks;
