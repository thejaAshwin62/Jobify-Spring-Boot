import { FormRow, SubmitBtn } from "../components";
import Wrapper from "../assets/wrappers/DashboardFormPage";
import { useDashboardContext } from "./DashboardLayout";
import { Form, redirect } from "react-router-dom";
import customFetch from "../utils/customFetch";
import { toast } from "react-toastify";

export const action = async ({ request }) => {
  const formData = await request.formData();
  const data = Object.fromEntries(formData);

  try {
    await customFetch.put(`/api/v1/user/${data.id}`, data);
    toast.success("Profile updated successfully");
    return redirect('/dashboard');
  } catch (error) {
    toast.error(error?.response?.data?.msg || "Something went wrong");
    return null;
  }
};

const Profile = () => {
  const { user } = useDashboardContext();
  
  if (!user) {
    return (
      <Wrapper>
        <div className="form">
          <h4 className="form-title">Loading...</h4>
        </div>
      </Wrapper>
    );
  }

  const { id, name, email, location } = user;

  return (
    <Wrapper>
      <Form method="post" className="form">
        <h4 className="form-title">profile</h4>

        <div className="form-center">
          <input type="hidden" name="id" value={id} />
          <FormRow type="text" name="name" defaultValue={name} />
          <FormRow type="email" name="email" defaultValue={email} />
          <FormRow type="text" name="location" defaultValue={location} />
          <SubmitBtn formBtn />
        </div>
      </Form>
    </Wrapper>
  );
};

export default Profile;