import { FaSuitcaseRolling, FaCalendarCheck } from "react-icons/fa";
import { useLoaderData, redirect } from "react-router-dom";
import customFetch from "../utils/customFetch";
import Wrapper from "../assets/wrappers/StatsContainer";
import { toast } from "react-toastify";
import { StatsItem } from "../components";

export const loader = async () => {
  try {
    const response = await customFetch.get("admin/app-stats");
    console.log(response);
    return response.data;
  } catch (error) {
    toast.error("you are not authorized to view this page");
    return redirect("/dashboard");
  }
};

const Admin = () => {
  const { User, Jobs } = useLoaderData();

  return (
    <Wrapper>
      <StatsItem
        title="current users"
        count={User}
        color="#e9b949"
        bcg="#fcefc7"
        icon={<FaSuitcaseRolling />}
      />
      <StatsItem
        title="total jobs"
        count={Jobs}
        color="#647acb"
        bcg="#e0e8f9"
        icon={<FaCalendarCheck />}
      />
    </Wrapper>
  );
};
export default Admin;
