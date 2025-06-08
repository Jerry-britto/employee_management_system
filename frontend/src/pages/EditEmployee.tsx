
import React, { useState, useEffect } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useToast } from "@/hooks/use-toast";
import { Employee } from "@/types/employee";
import { ArrowLeft, Save } from 'lucide-react';

const EditEmployee = () => {
  const [employeeName, setEmployeeName] = useState('');
  const [employeeDesignation, setEmployeeDesignation] = useState('');
  const [employeeJobRole, setEmployeeJobRole] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [isLoadingData, setIsLoadingData] = useState(true);
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  const { toast } = useToast();

  useEffect(() => {
    // Check authentication
    const isAuthenticated = localStorage.getItem('isAuthenticated');
    if (!isAuthenticated) {
      navigate('/');
      return;
    }

    // Load employee data
    if (id) {
      loadEmployee(id);
    }
  }, [navigate, id]);

  const loadEmployee = async (employeeId: string) => {
    try {
      const response = await fetch(`http://localhost:8000/employee/records/${employeeId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials:'include'
      });

      if (response.status >= 200 && response.status < 300) {
        const employee: Employee = await response.json();
        setEmployeeName(employee.employeeName);
        setEmployeeDesignation(employee.employeeDesignation);
        setEmployeeJobRole(employee.employeeJobRole);
        setIsLoadingData(false);
      } else {
        throw new Error(`Failed to fetch employee. Status: ${response.status}`);
      }
    } catch (error) {
      console.error('Error loading employee:', error);
      toast({
        title: "Error",
        description: "Failed to load employee data. Please check if the backend server is running.",
        variant: "destructive",
      });
      navigate('/dashboard');
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    // Validation
    if (!employeeName.trim() || !employeeDesignation.trim() || !employeeJobRole.trim()) {
      toast({
        title: "Error",
        description: "Please fill in all fields",
        variant: "destructive",
      });
      setIsLoading(false);
      return;
    }

    if (employeeName.length < 2) {
      toast({
        title: "Error",
        description: "Name must be at least 2 characters long",
        variant: "destructive",
      });
      setIsLoading(false);
      return;
    }

    try {
      const response = await fetch(`http://localhost:8000/employee/update`, {
        method: 'PUT',
        headers: { 
          'Content-Type': 'application/json' 
        },
        body: JSON.stringify({ 
          id,
          employeeName: employeeName.trim(), 
          employeeDesignation: employeeDesignation.trim(), 
          employeeJobRole: employeeJobRole.trim() 
        }),
        credentials:'include'
      });

      if (response.status >= 200 && response.status < 300) {
        toast({
          title: "Success",
          description: "Employee updated successfully",
        });
        navigate('/dashboard');
      } else {
        throw new Error(`Failed to update employee. Status: ${response.status}`);
      }
    } catch (error) {
      console.error('Error updating employee:', error);
      toast({
        title: "Error",
        description: "Failed to update employee. Please check if the backend server is running.",
        variant: "destructive",
      });
    }
    setIsLoading(false);
  };

  if (isLoadingData) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">Loading employee data...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center h-16">
            <Link to="/dashboard">
              <Button variant="ghost" size="sm" className="mr-4">
                <ArrowLeft className="h-4 w-4 mr-2" />
                Back to Dashboard
              </Button>
            </Link>
            <h1 className="text-2xl font-bold text-gray-900">Edit Employee</h1>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <div className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <Card>
          <CardHeader>
            <CardTitle className="text-2xl font-bold">Update Employee Details</CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-6">
              <div className="space-y-2">
                <Label htmlFor="employeeName" className="text-sm font-medium">
                  Employee Name <span className="text-red-500">*</span>
                </Label>
                <Input
                  id="employeeName"
                  type="text"
                  placeholder="Enter employee name"
                  value={employeeName}
                  onChange={(e) => setEmployeeName(e.target.value)}
                  className="h-12"
                  required
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="employeeDesignation" className="text-sm font-medium">
                  Designation <span className="text-red-500">*</span>
                </Label>
                <Input
                  id="employeeDesignation"
                  type="text"
                  placeholder="Enter designation"
                  value={employeeDesignation}
                  onChange={(e) => setEmployeeDesignation(e.target.value)}
                  className="h-12"
                  required
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="employeeJobRole" className="text-sm font-medium">
                  Job Role <span className="text-red-500">*</span>
                </Label>
                <Select value={employeeJobRole} onValueChange={setEmployeeJobRole} required>
                  <SelectTrigger className="h-12">
                    <SelectValue placeholder="Select a job role" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="Python Developer">Python Developer</SelectItem>
                    <SelectItem value="React Developer">React Developer</SelectItem>
                    <SelectItem value="Full Stack Developer">Full Stack Developer</SelectItem>
                    <SelectItem value="DevOps Consultant">DevOps Consultant</SelectItem>
                    <SelectItem value="Project Manager">Project Manager</SelectItem>
                    <SelectItem value="UI/UX Designer">UI/UX Designer</SelectItem>
                    <SelectItem value="Data Analyst">Data Analyst</SelectItem>
                    <SelectItem value="QA Tester">QA Tester</SelectItem>
                    <SelectItem value="HR Specialist">HR Specialist</SelectItem>
                    <SelectItem value="System Administrator">System Administrator</SelectItem>
                  </SelectContent>
                </Select>
              </div>

              <div className="flex gap-4 pt-6">
                <Button
                  type="submit"
                  className="flex-1 h-12 bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700"
                  disabled={isLoading}
                >
                  {isLoading ? (
                    <>
                      <Save className="mr-2 h-4 w-4 animate-spin" />
                      Updating Employee...
                    </>
                  ) : (
                    <>
                      <Save className="mr-2 h-4 w-4" />
                      Update Employee
                    </>
                  )}
                </Button>
                <Link to="/dashboard" className="flex-1">
                  <Button type="button" variant="outline" className="w-full h-12">
                    Cancel
                  </Button>
                </Link>
              </div>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default EditEmployee;
