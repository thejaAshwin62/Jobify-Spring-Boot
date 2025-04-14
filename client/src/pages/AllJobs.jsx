import { toast } from "react-toastify";
import { JobsContainer, SearchContainer } from "../components";
import customFetch from "../utils/customFetch";
import { useLoaderData } from "react-router-dom";
import { useContext, createContext } from "react";

export const loader = async ({ request }) => {
  const params = Object.fromEntries([
    ...new URL(request.url).searchParams.entries(),
  ]);
  
  try {
    const { data } = await customFetch.get("/job/all-jobs", {
      params,
    });
    console.log('All Jobs Data:', data);
    
    return {
      data: data || [],
      searchValues: { ...params },
    };
  } catch (error) {
    toast.error(error?.response?.data?.msg || "Error loading jobs");
    return { 
      data: [], 
      searchValues: { ...params } 
    };
  }
};

const AllJobsContext = createContext();

const AllJobs = () => {
  const { data, searchValues } = useLoaderData();

  return (
    <AllJobsContext.Provider value={{ data, searchValues }}>
      <SearchContainer />
      <JobsContainer />
    </AllJobsContext.Provider>
  );
};

export const useAllJobsContext = () => useContext(AllJobsContext);
export default AllJobs;
