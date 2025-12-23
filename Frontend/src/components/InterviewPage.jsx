import React, { useState, useEffect } from 'react';
import { Loader2, Send, ChevronLeft, Terminal, BrainCircuit } from 'lucide-react';
import { FeedbackCard } from './FeedbackCard';

export const InterviewPage = ({ sessionData, onBack }) => {
    const [questions, setQuestions] = useState([]);
    const [currentIdx, setCurrentIdx] = useState(0);
    const [answer, setAnswer] = useState('');
    const [loading, setLoading] = useState(true);
    const [evaluating, setEvaluating] = useState(false);
    const [evaluation, setEvaluation] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchQuestions = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/generate-interview', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        missingSkills: sessionData.missingSkills,
                        weakSections: sessionData.weakSections,
                        jobRole: sessionData.jobRole || "Software Engineer"
                    }),
                });

                if (!response.ok) throw new Error('Failed to generate interview questions.');

                const data = await response.json();
                setQuestions(data.questions || []);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchQuestions();
    }, [sessionData]);

    const handleEvaluate = async () => {
        if (!answer.trim()) return;

        setEvaluating(true);
        setError(null);
        setEvaluation(null);

        try {
            const response = await fetch('http://localhost:8080/api/evaluate-answer', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    question: questions[currentIdx].question,
                    answer: answer,
                    skill: questions[currentIdx].skill,
                    jobRole: sessionData.jobRole || "Software Engineer"
                }),
            });

            if (!response.ok) throw new Error('Failed to evaluate answer.');

            const data = await response.json();
            setEvaluation(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setEvaluating(false);
        }
    };

    if (loading) {
        return (
            <div className="min-h-[60vh] flex flex-col items-center justify-center gap-4 text-center">
                <div className="relative">
                    <div className="w-16 h-16 border-4 border-indigo-500/20 border-t-indigo-500 rounded-full animate-spin" />
                    <BrainCircuit className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 text-indigo-500" size={24} />
                </div>
                <div>
                    <h3 className="text-xl font-bold mb-1">Generating Targeted Questions</h3>
                    <p className="text-slate-500 dark:text-neutral-400">Our AI is analyzing your weaknesses to challenge you...</p>
                </div>
            </div>
        );
    }

    if (error && !questions.length) {
        return (
            <div className="min-h-[40vh] flex flex-col items-center justify-center text-rose-500">
                <p className="font-bold mb-4">{error}</p>
                <button onClick={onBack} className="text-indigo-500 font-bold flex items-center gap-2">
                    <ChevronLeft size={20} /> Go Back
                </button>
            </div>
        );
    }

    const currentQuestion = questions[currentIdx];

    return (
        <div className="max-w-4xl mx-auto space-y-10 py-12 animate-in fade-in duration-700">
            {/* Header */}
            <div className="flex items-center justify-between">
                <button
                    onClick={onBack}
                    className="flex items-center gap-2 text-sm font-bold uppercase tracking-wider opacity-60 hover:opacity-100 transition-opacity"
                >
                    <ChevronLeft size={16} /> Exit Interview
                </button>
                <div className="px-4 py-2 glass rounded-full text-xs font-bold uppercase tracking-widest text-indigo-500">
                    Question {currentIdx + 1} of {questions.length}
                </div>
            </div>

            {/* Question Card */}
            <div className="glass p-10 rounded-[40px] relative overflow-hidden">
                <div className="absolute top-0 right-0 p-10 opacity-5 pointer-events-none">
                    <Terminal size={140} />
                </div>

                <div className="space-y-6 relative">
                    <div className="inline-flex px-3 py-1 rounded-lg bg-indigo-500/10 text-indigo-500 text-[10px] font-black uppercase tracking-widest">
                        {currentQuestion?.type || "Technical"} • {currentQuestion?.skill}
                    </div>
                    <h2 className="text-3xl md:text-4xl font-bold tracking-tight leading-tight">
                        {currentQuestion?.question}
                    </h2>
                </div>
            </div>

            {/* Answer Box */}
            <div className="space-y-4">
                <div className="flex items-center justify-between px-2">
                    <span className="text-xs font-bold uppercase tracking-widest opacity-50">Your Answer</span>
                    <span className={cn("text-xs font-medium transition-opacity", answer.length > 0 ? "opacity-50" : "opacity-0")}>
                        {answer.length} characters
                    </span>
                </div>
                <textarea
                    value={answer}
                    onChange={(e) => setAnswer(e.target.value)}
                    placeholder="Type your detailed answer here..."
                    className="w-full h-64 p-8 rounded-[32px] bg-surface border-2 border-border focus:border-indigo-500/50 focus:ring-4 focus:ring-indigo-500/5 focus:outline-none resize-none transition-all text-lg leading-relaxed shadow-inner"
                    disabled={evaluating}
                />
            </div>

            {/* Action */}
            <div className="flex flex-col items-center gap-6">
                <button
                    onClick={handleEvaluate}
                    disabled={evaluating || !answer.trim()}
                    className="w-full max-w-md py-6 bg-indigo-500 text-white rounded-[24px] font-black text-xl hover:scale-[1.02] active:scale-[0.98] transition-all disabled:opacity-50 disabled:hover:scale-100 flex items-center justify-center gap-3 shadow-2xl shadow-indigo-500/25"
                >
                    {evaluating ? (
                        <>
                            <Loader2 className="animate-spin" size={24} />
                            Evaluating Response...
                        </>
                    ) : (
                        <>
                            <Send size={20} />
                            Evaluate Answer
                        </>
                    )}
                </button>

                {evaluation && currentIdx < questions.length - 1 && !evaluating && (
                    <button
                        onClick={() => {
                            setEvaluation(null);
                            setAnswer('');
                            setCurrentIdx(currentIdx + 1);
                            window.scrollTo({ top: 0, behavior: 'smooth' });
                        }}
                        className="text-indigo-500 font-bold hover:underline"
                    >
                        Next Question
                    </button>
                )}
            </div>

            {/* Feedback */}
            {evaluation && <FeedbackCard evaluation={evaluation} />}
        </div>
    );
};
