import { FormRow, FormRowSelect, SubmitBtn } from "../components";
import Wrapper from "../assets/wrappers/DashboardFormPage";
import { useLoaderData, useParams, redirect } from "react-router-dom";
import { JOB_STATUS, JOB_TYPE } from "../utils/constants"
import { Form } from "react-router-dom";
import { toast } from "react-toastify";
import customFetch from "../utils/customFetch";

export const loader = async ({ params }) => {
  const { id } = params;
  
  if (!id) {
    toast.error("No job ID provided");
    return redirect("/dashboard/all-jobs");
  }

  try {
    const { data } = await customFetch.get(`/job/${id}`);
    console.log('API Response:', data);
    
    if (!data) {
      toast.error("Job not found");
      return redirect("/dashboard/all-jobs");
    }
    return { job: data };
  } catch (error) {
    console.log('API Error:', error);
    toast.error(error?.response?.data?.msg || "Error loading job");
    return redirect("/dashboard/all-jobs");
  }
};

export const action = async ({ request, params }) => {
  const { id } = params;
  
  if (!id) {
    toast.error("No job ID provided");
    return redirect("/dashboard/all-jobs");
  }

  const formData = await request.formData();
  const data = Object.fromEntries(formData);
  try {
    await customFetch.put(`/job/${id}`, data);
    toast.success("Job edited successfully");
    return redirect("/dashboard/all-jobs");
  } catch (error) {
    toast.error(error?.response?.data?.msg || "Error editing job");
    return error;
  }
};

const EditJob = () => {
  const { job } = useLoaderData();
  
  if (!job) {
    return null;
  }

  return (
    <Wrapper>
      <Form method="post" className="form">
        <h4 className="form-title">edit job</h4>
        <div className="form-center">
          <FormRow type="text" name="position" defaultValue={job.position} />
          <FormRow type="text" name="company" defaultValue={job.company} />
          <FormRow
            type="text"
            labelText="job location"
            name="jobLocation"
            defaultValue={job.jobLocation}
          />
          <FormRowSelect
            name="jobStatus"
            labelText="job status"
            defaultValue={job.jobStatus}
            list={Object.values(JOB_STATUS)}
          />
          <FormRowSelect
            name="jobType"
            labelText="job type"
            defaultValue={job.jobType}
            list={Object.values(JOB_TYPE)}
          />
          <SubmitBtn formBtn/>
        </div>
      </Form>
    </Wrapper>
  );
};

export default EditJob;
